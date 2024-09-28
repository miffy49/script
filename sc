repeat task.wait() until game:IsLoaded()
repeat task.wait() until game.Players
repeat task.wait() until game.Players.LocalPlayer
repeat task.wait() until game.Players.LocalPlayer:FindFirstChild("PlayerGui")
repeat task.wait() until game.Players.LocalPlayer.PlayerGui:FindFirstChild("Main");

local function escolherTime(time)
    local VirtualInputManager = game:GetService("VirtualInputManager")

    while game:GetService("Players").LocalPlayer.Team == nil do
        local btn = game:GetService("Players").LocalPlayer.PlayerGui.Main.ChooseTeam.Container.Pirates.Frame.ViewportFrame
        local x, y = btn.AbsolutePosition.X + btn.AbsoluteSize.X / 2, btn.AbsolutePosition.Y + 50

        VirtualInputManager:SendMouseButtonEvent(x, y, 0, true, btn, 1)
        task.wait(0.5)
        VirtualInputManager:SendMouseButtonEvent(x, y, 0, false, btn, 1)
        task.wait(0.5)
    end

end

local function teleportar()
    local VirtualInputManager = game:GetService("VirtualInputManager")
    local btn = game:GetService("Players").LocalPlayer.PlayerGui.TopbarPlus.TopbarContainer.UnnamedIcon.IconButton
    local x, y = btn.AbsolutePosition.X + btn.AbsoluteSize.X / 2, btn.AbsolutePosition.Y + 50
    VirtualInputManager:SendMouseButtonEvent(x, y, 0, true, btn, 1)
    task.wait(0.5)
    VirtualInputManager:SendMouseButtonEvent(x, y, 0, false, btn, 1)
    task.wait(0.5)

    local player = game:GetService("Players").LocalPlayer
    local gui = player.PlayerGui.ServerBrowser.Frame
    gui.Filters.SearchRegion.TextBox.Text = "Brazil"
    wait(2)

    -- Obter o serverId do servidor como um atributo
    local serverGuiElement = gui.FakeScroll.Inside:GetChildren()[7].Join
    local serverId = serverGuiElement:GetAttribute("Job")

    -- Debug para verificar se pegamos o serverId correto
    print("Server ID encontrado:", serverId)

    -- Teletransportar o jogador para o servidor especificado
    local PlaceID = game.PlaceId
    local success, errorMessage = pcall(function()
        game:GetService("TeleportService"):TeleportToPlaceInstance(PlaceID, serverId, player)
    end)

    if not success then
        print("Erro ao tentar teleportar:", errorMessage)
    end
end

-- Função para substituir algumas letras por números de forma aleatória
local function substituirCaracteres(texto)
    return texto:gsub("[OAo]", function(c)
        if c == "O" or c == "o" then
            return math.random() > 0.5 and "0" or c
        elseif c == "A" or c == "a" then
            return math.random() > 0.5 and "4" or c
        else
            return c
        end
    end)
end

-- Função para alternar aleatoriamente entre maiúsculas e minúsculas
local function alternarMaiusculasMinusculas(texto)
    return texto:gsub("%a", function(c)
        if math.random() > 0.5 then
            return string.upper(c)
        else
            return string.lower(c)
        end
    end)
end

-- Função para escolher aleatoriamente se vamos aplicar uma das alterações sutis
local function alterarTexto(texto)
    if math.random() > 0.5 then
        return substituirCaracteres(texto)
    else
        return alternarMaiusculasMinusculas(texto)
    end
end

-- Mensagens a serem enviadas
local mensagens = {
    "",
    "CONTAS COM KITSUNE BARATO + LVL 2550 EM BLOXFRUITBRASIL . C 0 M . B R",
    "CONTAS COM GODHUMAN + LVL 2550 EM BLOXFRUITBRASIL . C 0 M . B R",
    "CONTAS LVL 2550 BARATO EM BLOXFRUITBRASIL . C 0 M . B R"
}

local function main()

    while true do
        task.wait(1)
        escolherTime("Pirates")
        task.wait(1)

        -- Enviar cada mensagem, com possível alteração
        for _, mensagem in ipairs(mensagens) do
            -- Alterar a mensagem de vez em quando
            local mensagemAlterada = alterarTexto(mensagem)
            
            -- Enviar a mensagem no chat
            game:GetService("ReplicatedStorage").DefaultChatSystemChatEvents.SayMessageRequest:FireServer(mensagemAlterada, "All")
            
            -- Espera 5 segundos antes de enviar a próxima mensagem
            task.wait(5)
        end

        -- Teleportar para um novo servidor após enviar as 3 mensagens
        teleportar()

        -- Esperar 10 segundos antes de repetir o ciclo
        task.wait(10)

    end


end

main()

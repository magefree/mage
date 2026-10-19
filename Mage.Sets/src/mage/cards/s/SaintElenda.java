package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

public final class SaintElenda extends CardImpl {

    public SaintElenda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SaintElendaSpellbookEffect()));
        this.addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.YOU,
                new SaintElendaAvatarEffect(), false, new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0)),
                new PlayerGainedLifeWatcher());
    }

    private SaintElenda(final SaintElenda card) {
        super(card);
    }

    @Override
    public SaintElenda copy() {
        return new SaintElenda(this);
    }
}

class SaintElendaSpellbookEffect extends OneShotEffect {

    SaintElendaSpellbookEffect() {
        super(Outcome.Benefit);
        staticText = "conjure a card of your choice from {this}'s spellbook into your hand. You may cast that card this turn without paying its mana cost";
    }

    private SaintElendaSpellbookEffect(final SaintElendaSpellbookEffect effect) {
        super(effect);
    }

    @Override
    public SaintElendaSpellbookEffect copy() {
        return new SaintElendaSpellbookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true, ChoiceHintType.CARD);
        choice.setMessage("Choose a card from Saint Elenda's spellbook");
        choice.setChoices(new HashSet<>(Arrays.asList("Faith's Fetters", "Invoke the Divine", "Ritual of Rejuvenation", "Inspiring Cleric")));
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        CardInfo info = CardRepository.instance.findCards(new CardCriteria().name(choice.getChoice())).stream().findFirst().orElse(null);
        if (info == null) {
            return false;
        }
        Card card = info.createCard();
        game.loadCards(Collections.singleton(card), player.getId());
        player.moveCards(card, Zone.HAND, source, game);
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.HAND, TargetController.YOU, Duration.EndOfTurn, true, true)
                .setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}

class SaintElendaAvatarEffect extends OneShotEffect {

    SaintElendaAvatarEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X white Avatar creature token, where X is the amount of life you gained this turn";
    }

    private SaintElendaAvatarEffect(final SaintElendaAvatarEffect effect) {
        super(effect);
    }

    @Override
    public SaintElendaAvatarEffect copy() {
        return new SaintElendaAvatarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        int amount = watcher == null ? 0 : watcher.getLifeGained(source.getControllerId());
        Token token = new CreatureToken(amount, amount, "X/X white Avatar creature token", SubType.AVATAR)
                .withColor("W").withName("Avatar Token");
        return token.putOntoBattlefield(1, game, source);
    }
}

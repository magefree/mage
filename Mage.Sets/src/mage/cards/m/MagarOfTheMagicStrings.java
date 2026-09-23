package mage.cards.m;

import java.util.Optional;
import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileSourceReplacementEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

public final class MagarOfTheMagicStrings extends CardImpl {

    public MagarOfTheMagicStrings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.PERFORMER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{B}{R}: Note the name of target instant or sorcery card in your graveyard and put it onto the battlefield face down.
        // It’s a 3/3 creature with
        //  “Whenever this creature deals combat damage to a player, you may create a copy of the card with the noted name. You may cast the copy without paying its mana cost”
        //  and “If this creature would leave the battlefield, exile it instead of putting it anywhere else.”
        Ability ability = new SimpleActivatedAbility(new MagarOfTheMagicStringsEffect(), new ManaCostsImpl<>("{1}{B}{R}"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private MagarOfTheMagicStrings(final MagarOfTheMagicStrings card) {
        super(card);
    }

    @Override
    public MagarOfTheMagicStrings copy() {
        return new MagarOfTheMagicStrings(this);
    }
}

class MagarOfTheMagicStringsEffect extends OneShotEffect {

    MagarOfTheMagicStringsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Note the name of target instant or sorcery card in your graveyard and put it onto the battlefield face down. It's a 3/3 creature with "
            + "\"Whenever this creature deals combat damage to a player, you may create a copy of the card with the noted name. You may cast the copy without paying its mana cost\" and"
            + "\"If this creature would leave the battlefield, exile it instead of putting it anywhere else.\"";
    }

    private MagarOfTheMagicStringsEffect(final MagarOfTheMagicStringsEffect effect) {
        super(effect);
    }

    @Override
    public MagarOfTheMagicStringsEffect copy() {
        return new MagarOfTheMagicStringsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        final Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        game.addEffect(new MagarOfTheMagicStringsCardTypeEffect(new MageObjectReference(card, game, 1)), source);
        player.moveCards(card, Zone.BATTLEFIELD, source, game, false, true, true, null);
        return true;
    }
}

class MagarOfTheMagicStringsCardTypeEffect extends BecomesFaceDownCreatureEffect {

    MagarOfTheMagicStringsCardTypeEffect(MageObjectReference objectReference) {
        super(null, objectReference, BecomesFaceDownCreatureEffect.FaceDownType.MANUAL_33);
    }

    private MagarOfTheMagicStringsCardTypeEffect(final MagarOfTheMagicStringsCardTypeEffect effect) {
        super(effect);
    }

    @Override
    public MagarOfTheMagicStringsCardTypeEffect copy() {
        return new MagarOfTheMagicStringsCardTypeEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.additionalAbilities.add(new DealsCombatDamageToAPlayerTriggeredAbility(new MagarOfTheMagicStringsCastEffect().setTargetPointer(new FixedTarget(this.objectReference)), false));
        this.additionalAbilities.add(new SimpleStaticAbility(new LeaveBattlefieldExileSourceReplacementEffect("this creature")));
    }
}

class MagarOfTheMagicStringsCastEffect extends OneShotEffect {

    MagarOfTheMagicStringsCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may create a copy of the card with the noted name. You may cast the copy without paying its mana cost";
    }

    private MagarOfTheMagicStringsCastEffect(final MagarOfTheMagicStringsCastEffect effect) {
        super(effect);
    }

    @Override
    public MagarOfTheMagicStringsCastEffect copy() {
        return new MagarOfTheMagicStringsCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Player player = game.getPlayer(source.getControllerId());
        final Card card = Optional.of(game.getCard(this.getTargetPointer().getFirst(game, source))).map(Card::getMainCard).orElse(null);
        if (player == null || card == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Cast copy of " + card.getName() + " without paying its mana cost?", source, game)) {
            return true;
        }
        final CardInfo cardInfo = CardRepository.instance.findCardWithPreferredSetAndNumber(card.getName(), card.getExpansionSetCode(), card.getCardNumber());
        if (cardInfo == null) {
            return false;
        }
        final Card newCard = cardInfo.createCard();
        if (newCard == null) {
            return false;
        }
        final Card cardCopy = game.copyCard(newCard, source, source.getControllerId());
        cardCopy.setZone(Zone.GRAVEYARD, game);
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(cardCopy, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + cardCopy.getId(), null);
        return true;
    }
}

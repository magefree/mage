package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Will
 */
public final class GyrusWakerOfCorpses extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("target creature card with lesser power from your graveyard");

    static {
        filter.add(new GyrusWakerOfCorpsesPowerLessThanSourcePredicate());
    }

    public GyrusWakerOfCorpses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Gyrus, Walker of Corpses enters the battlefield with a number of +1/+1 counters on it equal to the amount of mana spent to cast it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), ManaSpentToCastCount.instance, true);
        effect.setText("with a number of +1/+1 counters on it equal to the amount of mana spent to cast it");
        this.addAbility(new EntersBattlefieldAbility(effect));

        // Whenever Gyrus attacks, you may exile target creature card with lesser power from your graveyard. If you do, create a token that’s a copy of that card and that’s tapped and attacking. Exile the token at the end of combat.
        Ability ability = new AttacksTriggeredAbility(new GyrusWakerOfCorpsesEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private GyrusWakerOfCorpses(final GyrusWakerOfCorpses card) {
        super(card);
    }

    @Override
    public GyrusWakerOfCorpses copy() {
        return new GyrusWakerOfCorpses(this);
    }
}

class GyrusWakerOfCorpsesEffect extends OneShotEffect {

    public GyrusWakerOfCorpsesEffect() {
        super(Outcome.Copy);
        this.staticText = "exile target creature card with lesser power from your graveyard. If you do, create a token that's a copy of that card and that's tapped and attacking. Exile the token at the end of combat.";
    }

    public GyrusWakerOfCorpsesEffect(final GyrusWakerOfCorpsesEffect effect) {
        super(effect);
    }

    @Override
    public GyrusWakerOfCorpsesEffect copy() {
        return new GyrusWakerOfCorpsesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Card card = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true, 1, true, true);
        effect.setTargetPointer(new FixedTarget(card, game));
        effect.apply(game, source);
        for (Permanent addedToken : effect.getAddedPermanents()) {
            Effect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
            new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), false).apply(game, source);
        }
        return true;
    }
}

class GyrusWakerOfCorpsesPowerLessThanSourcePredicate implements ObjectSourcePlayerPredicate<Card> {

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(input.getSourceId());
        return sourcePermanent != null && input.getObject().getPower().getValue() < sourcePermanent.getPower().getValue();
    }

    @Override
    public String toString() {
        return "lesser power";
    }
}

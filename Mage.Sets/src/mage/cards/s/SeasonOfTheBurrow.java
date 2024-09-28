package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.RabbitToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SeasonOfTheBurrow extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("permanent card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public SeasonOfTheBurrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMaxPawPrints(5);
        this.getSpellAbility().getModes().setMinModes(0);
        this.getSpellAbility().getModes().setMaxModes(5);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // {P} -- Create a 1/1 white Rabbit creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RabbitToken()));
        this.getSpellAbility().getModes().getMode().withPawPrintValue(1);

        // {P}{P} -- Exile target nonland permanent. Its controller draws a card.
        Mode mode2 = new Mode(new ExileTargetEffect());
        mode2.addTarget(new TargetNonlandPermanent());
        mode2.addEffect(new DrawCardTargetControllerEffect(1));
        this.getSpellAbility().addMode(mode2.withPawPrintValue(2));

        // {P}{P}{P} -- Return target permanent card with mana value 3 or less from your graveyard to the battlefield with an indestructible counter on it.
        Mode mode3 = new Mode(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.INDESTRUCTIBLE.createInstance()));
        mode3.addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addMode(mode3.withPawPrintValue(3));
    }

    private SeasonOfTheBurrow(final SeasonOfTheBurrow card) {
        super(card);
    }

    @Override
    public SeasonOfTheBurrow copy() {
        return new SeasonOfTheBurrow(this);
    }
}

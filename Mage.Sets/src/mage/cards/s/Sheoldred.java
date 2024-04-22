package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Sheoldred extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("nontoken creature or planeswalker");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public Sheoldred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.t.TheTrueScriptures.class;

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Sheoldred enters the battlefield, each opponent sacrifices a nontoken creature or planeswalker.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsEffect(filter)));

        // {4}{B}: Exile Sheoldred, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery and only if an opponent has eight or more cards in their graveyard.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{4}{B}"), CardsInOpponentGraveyardCondition.EIGHT
        ).setTiming(TimingRule.SORCERY).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));
    }

    private Sheoldred(final Sheoldred card) {
        super(card);
    }

    @Override
    public Sheoldred copy() {
        return new Sheoldred(this);
    }
}

package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoraciousGreatshark extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact or creature spell");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public VoraciousGreatshark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SHARK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Voracious Greatshark enters the battlefield, counter target artifact or creature spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CounterTargetEffect());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private VoraciousGreatshark(final VoraciousGreatshark card) {
        super(card);
    }

    @Override
    public VoraciousGreatshark copy() {
        return new VoraciousGreatshark(this);
    }
}

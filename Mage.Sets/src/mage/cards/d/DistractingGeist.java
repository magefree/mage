package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DistractingGeist extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public DistractingGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.c.CleverDistraction.class;

        // Whenever Distracting Geist attacks, tap target creature defending player controls.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Disturb {4}{W}
        this.addAbility(new DisturbAbility(this, "{4}{W}"));
    }

    private DistractingGeist(final DistractingGeist card) {
        super(card);
    }

    @Override
    public DistractingGeist copy() {
        return new DistractingGeist(this);
    }
}

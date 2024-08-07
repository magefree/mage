package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.predicate.other.ArtifactSourcePredicate;
import mage.filter.predicate.other.NotManaAbilityPredicate;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class KurkeshOnakkeAncient extends CardImpl {
    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("an ability of an artifact, if it isn't a mana ability");

    static {
        filter.add(NotManaAbilityPredicate.instance);
        filter.add(ArtifactSourcePredicate.instance);
    }

    public KurkeshOnakkeAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you activate an ability of an artifact, if it isn't a mana ability, you may pay {R}.  If you do, copy that ability.  You may choose new targets for the copy.
        this.addAbility(new ActivateAbilityTriggeredAbility(new DoIfCostPaid(new CopyStackObjectEffect(), new ManaCostsImpl<>("{R}")), filter, SetTargetPointer.SPELL));
    }

    private KurkeshOnakkeAncient(final KurkeshOnakkeAncient card) {
        super(card);
    }

    @Override
    public KurkeshOnakkeAncient copy() {
        return new KurkeshOnakkeAncient(this);
    }
}

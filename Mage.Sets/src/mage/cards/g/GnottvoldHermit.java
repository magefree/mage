package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnottvoldHermit extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GnottvoldHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.TROLL}, "{3}{G}",
                "Chrome Host Hulk",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.TROLL}, "UG"
        );

        // Gnottvold Hermit
        this.getLeftHalfCard().setPT(4, 4);

        // {5}{U/P}: Transform Gnottvold Hermit. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{U/P}")));

        // Chrome Host Hulk
        this.getRightHalfCard().setPT(5, 5);

        // Whenever Chrome Host Hulk attacks, up to one other target creature has base power and toughness 5/5 until end of turn.
        Ability ability = new AttacksTriggeredAbility(
                new SetBasePowerToughnessTargetEffect(5, 5, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private GnottvoldHermit(final GnottvoldHermit card) {
        super(card);
    }

    @Override
    public GnottvoldHermit copy() {
        return new GnottvoldHermit(this);
    }
}

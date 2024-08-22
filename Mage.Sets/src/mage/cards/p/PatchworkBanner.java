package mage.cards.p;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatchworkBanner extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PatchworkBanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As Patchwork Banner enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllOfChosenSubtypeEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private PatchworkBanner(final PatchworkBanner card) {
        super(card);
    }

    @Override
    public PatchworkBanner copy() {
        return new PatchworkBanner(this);
    }
}

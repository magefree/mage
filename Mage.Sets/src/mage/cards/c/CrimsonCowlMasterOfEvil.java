package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.VillainToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CrimsonCowlMasterOfEvil extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken Villains you control");

    static {
        filter.add(SubType.VILLAIN.getPredicate());
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CrimsonCowlMasterOfEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever one or more nontoken Villains you control attack a player, you create a 2/1 black Villain creature token with menace.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new CreateTokenEffect(new VillainToken()),
            1, filter
        ).setTriggerPhrase("Whenever one more nontoken Villains you control attack a player, "));
    }

    private CrimsonCowlMasterOfEvil(final CrimsonCowlMasterOfEvil card) {
        super(card);
    }

    @Override
    public CrimsonCowlMasterOfEvil copy() {
        return new CrimsonCowlMasterOfEvil(this);
    }
}

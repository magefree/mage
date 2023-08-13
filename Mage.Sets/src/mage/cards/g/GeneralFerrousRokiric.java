package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HexproofFromMonocoloredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.permanent.token.RedWhiteGolemToken;

/**
 *
 * @author weirddan455
 */
public final class GeneralFerrousRokiric extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a multicolored spell");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public GeneralFerrousRokiric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Hexproof from monocolored
        this.addAbility(HexproofFromMonocoloredAbility.getInstance());

        // Whenever you cast a multicolored spell, create a 4/4 red and white Golem artifact creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new RedWhiteGolemToken()), filter, false));
    }

    private GeneralFerrousRokiric(final GeneralFerrousRokiric card) {
        super(card);
    }

    @Override
    public GeneralFerrousRokiric copy() {
        return new GeneralFerrousRokiric(this);
    }
}

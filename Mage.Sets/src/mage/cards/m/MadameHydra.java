package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.game.permanent.token.VillainToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MadameHydra extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Villain spell");

    static {
        filter.add(SubType.VILLAIN.getPredicate());
    }

    public MadameHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a Villain spell, create a 2/1 black Villain creature token with menace.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new VillainToken()), filter, false));
    }

    private MadameHydra(final MadameHydra card) {
        super(card);
    }

    @Override
    public MadameHydra copy() {
        return new MadameHydra(this);
    }
}

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class BlackDragon extends CardImpl {

    public BlackDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Acid Breath — When Black Dragon enters the battlefield, target creature an opponent controls gets -3/-3 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-3, -3));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Acid Breath"));
    }

    private BlackDragon(final BlackDragon card) {
        super(card);
    }

    @Override
    public BlackDragon copy() {
        return new BlackDragon(this);
    }
}

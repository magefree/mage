package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShamblingGhast extends CardImpl {

    public ShamblingGhast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Shambling Ghast dies, choose one —
        // • Brave the Stench — Target creature an opponent controls -1/-1 until end of turn.
        Ability ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-1, -1));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.withFirstModeFlavorWord("Brave the Stench");

        // • Search the Body — Create a Treasure token.
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken())).withFlavorWord("Search the Body"));
        this.addAbility(ability);
    }

    private ShamblingGhast(final ShamblingGhast card) {
        super(card);
    }

    @Override
    public ShamblingGhast copy() {
        return new ShamblingGhast(this);
    }
}

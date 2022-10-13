
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class GlissaSunseeker extends CardImpl {

    public GlissaSunseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {tap}: Destroy target artifact if its converted mana cost is equal to the amount of mana in your mana pool.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GlissaSunseekerEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private GlissaSunseeker(final GlissaSunseeker card) {
        super(card);
    }

    @Override
    public GlissaSunseeker copy() {
        return new GlissaSunseeker(this);
    }
}

class GlissaSunseekerEffect extends OneShotEffect {

    public GlissaSunseekerEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact if its mana value is equal to the amount of unspent mana you have";
    }

    public GlissaSunseekerEffect(final GlissaSunseekerEffect effect) {
        super(effect);
    }

    @Override
    public GlissaSunseekerEffect copy() {
        return new GlissaSunseekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || permanent == null) {
            return false;
        }
        ManaPool pool = controller.getManaPool();
        int blackMana = pool.getBlack();
        int whiteMana = pool.getWhite();
        int blueMana = pool.getBlue();
        int greenMana = pool.getGreen();
        int redMana = pool.getRed();
        int colorlessMana = pool.getColorless();
        int manaPoolTotal = blackMana + whiteMana + blueMana + greenMana + redMana + colorlessMana;
        if (permanent.getManaValue() == manaPoolTotal) {
            return permanent.destroy(source, game, false);
        }
        return false;
    }
}

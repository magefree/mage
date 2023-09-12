package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class FloweringLumberknot extends CardImpl {

    public FloweringLumberknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flowering Lumberknot can't attack or block unless it's paired with a creature with soulbond.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FloweringLumberknotEffect()));
    }

    private FloweringLumberknot(final FloweringLumberknot card) {
        super(card);
    }

    @Override
    public FloweringLumberknot copy() {
        return new FloweringLumberknot(this);
    }
}

class FloweringLumberknotEffect extends RestrictionEffect {

    public FloweringLumberknotEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless it's paired with a creature with soulbond";
    }

    private FloweringLumberknotEffect(final FloweringLumberknotEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (permanent.getPairedCard() != null) {
                Permanent paired = permanent.getPairedCard().getPermanent(game);
                if (paired != null) {
                    boolean found = false;
                    for (Ability ability : paired.getAbilities(game)) {
                        if (ability instanceof SoulbondAbility) {
                            found = true;
                            break;
                        }
                    }
                    // paired => can attack or block
                    return !found;
                }
            }
            // can't attack or block 
            return true;
        }
        return false;

    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public FloweringLumberknotEffect copy() {
        return new FloweringLumberknotEffect(this);
    }
}

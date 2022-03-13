package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author icetc
 */
public final class Okk extends CardImpl {

    public Okk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Okk can't attack unless a creature with greater power also attacks.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OkkAttackEffect()));

        // Okk can't block unless a creature with greater power also blocks.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OkkBlockEffect()));
    }

    private Okk(final Okk card) {
        super(card);
    }

    @Override
    public Okk copy() {
        return new Okk(this);
    }
}

class OkkAttackEffect extends RestrictionEffect {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("Attacking creatures");

    public OkkAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless a creature with greater power also attacks";
    }

    public OkkAttackEffect(final OkkAttackEffect effect) {
        super(effect);
    }

    @Override
    public OkkAttackEffect copy() {
        return new OkkAttackEffect(this);
    }

    @Override
    public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            // Search for an attacking creature with greater power
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (creature.getPower().getValue() > permanent.getPower().getValue()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

class OkkBlockEffect extends RestrictionEffect {

    private static final FilterBlockingCreature filter = new FilterBlockingCreature("Blocking creatures");

    public OkkBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block unless a creature with greater power also blocks.";
    }

    public OkkBlockEffect(final OkkBlockEffect effect) {
        super(effect);
    }

    @Override
    public OkkBlockEffect copy() {
        return new OkkBlockEffect(this);
    }

    @Override
    public boolean canBlockCheckAfter(Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            // Search for a blocking creature with greater power
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (creature.getPower().getValue() > permanent.getPower().getValue()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

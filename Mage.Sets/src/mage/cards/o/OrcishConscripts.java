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
import mage.filter.common.FilterBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class OrcishConscripts extends CardImpl {

    public OrcishConscripts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        
        this.subtype.add(SubType.ORC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Orcish Conscripts can't attack unless at least two other creatures attack.
        this.addAbility(new SimpleStaticAbility(new OrcishConscriptsAttackEffect()));

        // Orcish Conscripts can't block unless at least two other creatures block.
        this.addAbility(new SimpleStaticAbility(new OrcishConscriptsBlockEffect()));
    }

    private OrcishConscripts(final OrcishConscripts card) {
        super(card);
    }

    @Override
    public OrcishConscripts copy() {
        return new OrcishConscripts(this);
    }
}

class OrcishConscriptsAttackEffect extends RestrictionEffect {

    OrcishConscriptsAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless at least two other creatures attack";
    }

    private OrcishConscriptsAttackEffect(final OrcishConscriptsAttackEffect effect) {
        super(effect);
    }

    @Override
    public OrcishConscriptsAttackEffect copy() {
        return new OrcishConscriptsAttackEffect(this);
    }

    @Override
    public boolean canAttackCheckAfter(int numberOfAttackers, Ability source, Game game, boolean canUseChooseDialogs) {
        return numberOfAttackers > 2;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return source.getSourceId().equals(permanent.getId());
    }
}

class OrcishConscriptsBlockEffect extends RestrictionEffect {

    private static final FilterBlockingCreature filter = new FilterBlockingCreature("Blocking creatures");

    public OrcishConscriptsBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't block unless at least two other creatures block";
    }

    private OrcishConscriptsBlockEffect(final OrcishConscriptsBlockEffect effect) {
        super(effect);
    }

    @Override
    public OrcishConscriptsBlockEffect copy() {
        return new OrcishConscriptsBlockEffect(this);
    }

    @Override
    public boolean canBlockCheckAfter(Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).size() <= 2;
        }
        return false;
    }
}

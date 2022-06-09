package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.*;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author noahg
 */
public final class ThickSkinnedGoblin extends CardImpl {

    public ThickSkinnedGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // You may pay {0} rather than pay the echo cost for permanents you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThickSkinnedGoblinCostModificationEffect()));

        // {R}: Thick-Skinned Goblin gains protection from red until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(ProtectionAbility.from(ObjectColor.RED), Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private ThickSkinnedGoblin(final ThickSkinnedGoblin card) {
        super(card);
    }

    @Override
    public ThickSkinnedGoblin copy() {
        return new ThickSkinnedGoblin(this);
    }
}

class ThickSkinnedGoblinCostModificationEffect extends AsThoughEffectImpl {

    public ThickSkinnedGoblinCostModificationEffect(){
        super(AsThoughEffectType.PAY_0_ECHO, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "You may pay {0} rather than pay the echo cost for permanents you control.";
    }

    public ThickSkinnedGoblinCostModificationEffect(ThickSkinnedGoblinCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent sourcePermanent = game.getPermanent(sourceId);
        return sourcePermanent != null && sourcePermanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThickSkinnedGoblinCostModificationEffect copy() {
        return new ThickSkinnedGoblinCostModificationEffect(this);
    }
}
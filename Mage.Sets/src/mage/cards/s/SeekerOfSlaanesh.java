package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jimga150
 */
public final class SeekerOfSlaanesh extends CardImpl {

    public SeekerOfSlaanesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Allure of Slaanesh -- Each opponent must attack with at least one creature each combat if able.
        addAbility(new SimpleStaticAbility(new SeekerOfSlaaneshForceAttackEffect(Duration.WhileOnBattlefield))
                .withFlavorWord("Allure of Slaanesh"));
    }

    private SeekerOfSlaanesh(final SeekerOfSlaanesh card) {
        super(card);
    }

    @Override
    public SeekerOfSlaanesh copy() {
        return new SeekerOfSlaanesh(this);
    }
}

// Based on TroveOfTemptationForceAttackEffect
class SeekerOfSlaaneshForceAttackEffect extends RequirementEffect {

    SeekerOfSlaaneshForceAttackEffect(Duration duration) {
        super(duration, true);
        staticText = "Each opponent must attack with at least one creature each combat if able.";
    }

    private SeekerOfSlaaneshForceAttackEffect(final SeekerOfSlaaneshForceAttackEffect effect) {
        super(effect);
    }

    @Override
    public SeekerOfSlaaneshForceAttackEffect copy() {
        return new SeekerOfSlaaneshForceAttackEffect(this);
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(game.getActivePlayerId(), game);
    }

}

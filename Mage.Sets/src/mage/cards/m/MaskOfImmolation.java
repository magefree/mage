package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.YoungPyromancerElementalToken;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaskOfImmolation extends CardImpl {

    public MaskOfImmolation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Mask of Immolation enters the battlefield, create a 1/1 red Elemental creature token, then attach Mask of Immolation to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MaskOfImmolationEffect()));

        // Equipped creature has "Sacrifice this creature: It deals 1 damage to any target."
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ability, AttachmentType.EQUIPMENT
        ).setText("Equipped creature has \"Sacrifice this creature: It deals 1 damage to any target.\"")));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private MaskOfImmolation(final MaskOfImmolation card) {
        super(card);
    }

    @Override
    public MaskOfImmolation copy() {
        return new MaskOfImmolation(this);
    }
}

class MaskOfImmolationEffect extends CreateTokenEffect {

    MaskOfImmolationEffect() {
        super(new YoungPyromancerElementalToken());
        staticText = "create a 1/1 red Elemental creature token, then attach {this} to it.";
    }

    private MaskOfImmolationEffect(final MaskOfImmolationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !super.apply(game, source)) {
            return false;
        }
        Permanent p = game.getPermanent(this.getLastAddedTokenId());
        if (p == null) {
            return false;
        }
        p.addAttachment(source.getSourceId(), game);
        return true;
    }

    @Override
    public MaskOfImmolationEffect copy() {
        return new MaskOfImmolationEffect(this);
    }
}

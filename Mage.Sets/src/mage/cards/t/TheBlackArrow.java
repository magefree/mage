package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTarget;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class TheBlackArrow extends CardImpl {

    public TheBlackArrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When The Black Arrow enters, it deals 1 damage to any target. If a Dragon is dealt damage this way, destroy it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TheBlackArrowEffect());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Equipped creature gets +1/+1 and has reach.
        Ability equipAbility = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        equipAbility.addEffect(
            new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and has reach")
        );
        this.addAbility(equipAbility);

        // Equip {1}
        this.addAbility(new EquipAbility(1));

    }

    private TheBlackArrow(final TheBlackArrow card) {
        super(card);
    }

    @Override
    public TheBlackArrow copy() {
        return new TheBlackArrow(this);
    }
}

class TheBlackArrowEffect extends OneShotEffect {

    TheBlackArrowEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 1 damage to any target. If a Dragon is dealt damage this way, destroy it";
    }

    private TheBlackArrowEffect(final TheBlackArrowEffect effect) {
        super(effect);
    }

    @Override
    public TheBlackArrowEffect copy() {
        return new TheBlackArrowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            boolean dealtDamage = permanent.damage(1, source.getSourceId(), source, game) > 0;
            if (dealtDamage && permanent.hasSubtype(SubType.DRAGON, game)) {
                permanent.destroy(source, game, false);
            }
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(1, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }
}

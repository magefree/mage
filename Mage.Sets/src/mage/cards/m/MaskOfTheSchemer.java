package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaskOfTheSchemer extends CardImpl {

    public MaskOfTheSchemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage to a player, it connives X, where X is the amount of damage it dealt to that player.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new MaskOfTheSchemerEffect(), "equipped", false
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private MaskOfTheSchemer(final MaskOfTheSchemer card) {
        super(card);
    }

    @Override
    public MaskOfTheSchemer copy() {
        return new MaskOfTheSchemer(this);
    }
}

class MaskOfTheSchemerEffect extends OneShotEffect {

    MaskOfTheSchemerEffect() {
        super(Outcome.Benefit);
        staticText = "it connives X, where X is the amount of damage it dealt to that player. " +
                "<i>(Draw X card, then discard X cards. Put a +1/+1 counter " +
                "on that creature for each nonland card discarded this way.)</i>";
    }

    private MaskOfTheSchemerEffect(final MaskOfTheSchemerEffect effect) {
        super(effect);
    }

    @Override
    public MaskOfTheSchemerEffect copy() {
        return new MaskOfTheSchemerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = source.getSourcePermanentOrLKI(game);
        int damage = (Integer) getValue("damage");
        if (equipment == null || damage < 1) {
            return false;
        }
        Permanent permanent = game.getPermanent(equipment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        return permanent != null && ConniveSourceEffect.connive(permanent, damage, source, game);
    }
}

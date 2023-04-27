package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeAttachmentCost;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.Outcome;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class TrickstersTalisman extends CardImpl {

    public TrickstersTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has "Whenever this creature deals combat damage to a player, you may sacrifice Trickster's Talisman. If you do, create a token that's a copy of this creature."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new TrickstersTalismanEffect());
        this.addAbility(ability.withFlavorWord("Invoke Duplicity"));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private TrickstersTalisman(final TrickstersTalisman card) {
        super(card);
    }

    @Override
    public TrickstersTalisman copy() {
        return new TrickstersTalisman(this);
    }
}

class TrickstersTalismanEffect extends GainAbilityWithAttachmentEffect {

    TrickstersTalismanEffect() {
        super("and has \"Whenever this creature deals combat damage to a player, " +
                        "you may sacrifice {this}. If you do, create a token that's a copy of this creature.\"",
                (Effect) null, null, new SacrificeAttachmentCost());
    }

    private TrickstersTalismanEffect(final TrickstersTalismanEffect effect) {
        super(effect);
    }

    @Override
    public TrickstersTalismanEffect copy() {
        return new TrickstersTalismanEffect(this);
    }

    @Override
    protected Ability makeAbility(Game game, Ability source) {
        if (game == null || source == null) {
            return null;
        }
        return new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new CreateTokenCopySourceEffect(), useAttachedCost.setMageObjectReference(source, game)
        ), false, "Whenever this creature deals combat damage to a player, you may sacrifice "
                + source.getSourcePermanentIfItStillExists(game).getName()
                + ". If you do, create a token that's a copy of this creature.", false);
    }
}

package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathToRedemption extends CardImpl {

    public PathToRedemption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature can't attack or block.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockAttachedEffect(AttachmentType.AURA)));

        // {5}, Sacrifice this Aura: Exile enchanted creature. Create a 1/1 white Ally creature token. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new ExileAttachedEffect(), new GenericManaCost(5), MyTurnCondition.instance
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new CreateTokenEffect(new AllyToken()));
        this.addAbility(ability);
    }

    private PathToRedemption(final PathToRedemption card) {
        super(card);
    }

    @Override
    public PathToRedemption copy() {
        return new PathToRedemption(this);
    }
}

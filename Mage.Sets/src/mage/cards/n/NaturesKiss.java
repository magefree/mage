package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileTopCardOfGraveyardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NaturesKiss extends CardImpl {

    public NaturesKiss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // {1}, Exile the top card of your graveyard: Enchanted creature gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostEnchantedEffect(
                1, 1, Duration.EndOfTurn
        ), new GenericManaCost(1));
        ability.addCost(new ExileTopCardOfGraveyardCost());
        this.addAbility(ability);
    }

    private NaturesKiss(final NaturesKiss card) {
        super(card);
    }

    @Override
    public NaturesKiss copy() {
        return new NaturesKiss(this);
    }
}

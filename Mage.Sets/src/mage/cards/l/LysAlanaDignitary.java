package mage.cards.l;

import mage.MageInt;
import mage.Mana;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LysAlanaDignitary extends CardImpl {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(1, new FilterCard(SubType.ELF));
    private static final Hint hint = new ConditionHint(condition, "There is an Elf card in your graveyard");

    public LysAlanaDignitary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As an additional cost to cast this spell, behold an Elf or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                "behold an Elf or pay {2}",
                new BeholdCost(SubType.ELF), new GenericManaCost(2)
        ));

        // {T}: Add {G}{G}. Activate only if there is an Elf card in your graveyard.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana(2)), new TapSourceCost(), condition
        ).addHint(hint));
    }

    private LysAlanaDignitary(final LysAlanaDignitary card) {
        super(card);
    }

    @Override
    public LysAlanaDignitary copy() {
        return new LysAlanaDignitary(this);
    }
}

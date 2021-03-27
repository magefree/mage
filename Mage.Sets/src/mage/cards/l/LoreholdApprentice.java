package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreholdApprentice extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.SPIRIT, "");

    public LoreholdApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, until end of turn, Spirit creatures you control gain "{T}: This creature deals 1 damage to each opponent."
        this.addAbility(new MagecraftAbility(new GainAbilityControlledEffect(
                new SimpleActivatedAbility(new DamagePlayersEffect(
                        1, TargetController.OPPONENT, "this creature"
                ), new TapSourceCost()), Duration.WhileOnBattlefield, filter
        ).setText("until end of turn, Spirit creatures you control gain \"{T}: This creature deals 1 damage to each opponent.\"")));
    }

    private LoreholdApprentice(final LoreholdApprentice card) {
        super(card);
    }

    @Override
    public LoreholdApprentice copy() {
        return new LoreholdApprentice(this);
    }
}

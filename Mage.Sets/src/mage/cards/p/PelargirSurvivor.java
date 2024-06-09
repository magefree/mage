package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PelargirSurvivor extends CardImpl {

    public PelargirSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add one mana of any color. Spend this mana only to cast an instant or sorcery spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new InstantOrSorcerySpellManaBuilder()));

        // {5}{U}, {T}: Target player mills three cards.
        Ability ability = new SimpleActivatedAbility(
                new MillCardsTargetEffect(3), new ManaCostsImpl<>("{5}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private PelargirSurvivor(final PelargirSurvivor card) {
        super(card);
    }

    @Override
    public PelargirSurvivor copy() {
        return new PelargirSurvivor(this);
    }
}

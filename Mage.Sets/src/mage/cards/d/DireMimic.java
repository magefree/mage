package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DireMimic extends CardImpl {

    public DireMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.TREASURE);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // {T}, Sacrifice Dire Mimic: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {3}: Dire Mimic becomes a Shapeshifter artifact creature with base power and toughness 5/5 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        5, 5, "Shapeshifter artifact creature " +
                        "with base power and toughness 5/5", SubType.SHAPESHIFTER
                ).withType(CardType.ARTIFACT), "", Duration.EndOfTurn
        ), new GenericManaCost(3)));
    }

    private DireMimic(final DireMimic card) {
        super(card);
    }

    @Override
    public DireMimic copy() {
        return new DireMimic(this);
    }
}

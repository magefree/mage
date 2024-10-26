package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class MagusOfTheWill extends CardImpl {

    public MagusOfTheWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{B}, {T}, Exile Magus of the Will: Until end of turn, you may play lands and cast spells from your graveyard.
        // If a card would be put into your graveyard from anywhere else this turn, exile that card instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                PlayFromGraveyardControllerEffect.playLandsAndCastSpells(Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}{B}"));
        ability.addEffect(new GraveyardFromAnywhereExileReplacementEffect(Duration.EndOfTurn));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private MagusOfTheWill(final MagusOfTheWill card) {
        super(card);
    }

    @Override
    public MagusOfTheWill copy() {
        return new MagusOfTheWill(this);
    }
}

package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CabalInterrogator extends CardImpl {

    public CabalInterrogator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{B}, {tap}: Target player reveals X cards from their hand and you choose one of them. That player discards that card.
        // Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DiscardCardYouChooseTargetEffect(TargetController.ANY, ManacostVariableValue.REGULAR),
                new ManaCostsImpl<>("{X}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CabalInterrogator(final CabalInterrogator card) {
        super(card);
    }

    @Override
    public CabalInterrogator copy() {
        return new CabalInterrogator(this);
    }
}

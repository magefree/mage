
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class DimirGuildmage extends CardImpl {

    public DimirGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/B}{U/B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{U}: Target player draws a card. Activate this ability only any time you could cast a sorcery.
        Ability firstAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(1), new ManaCostsImpl<>("{3}{U}"));
        firstAbility.addTarget(new TargetPlayer());
        this.addAbility(firstAbility);
        // {3}{B}: Target player discards a card. Activate this ability only any time you could cast a sorcery.
        Ability secondAbility = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl<>("{3}{B}"));
        secondAbility.addTarget(new TargetPlayer());
        this.addAbility(secondAbility);
    }

    private DimirGuildmage(final DimirGuildmage card) {
        super(card);
    }

    @Override
    public DimirGuildmage copy() {
        return new DimirGuildmage(this);
    }
}

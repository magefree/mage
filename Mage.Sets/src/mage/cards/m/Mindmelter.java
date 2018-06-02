
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class Mindmelter extends CardImpl {

    public Mindmelter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Mindmelter can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // {3}{C}: Target opponent exiles a card from their hand. Activate this ability only any time you could cast a sorcery.
        Effect effect = new ExileFromZoneTargetEffect(Zone.HAND, null, "", new FilterCard());
        effect.setText("Target opponent exiles a card from their hand");
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{3}{C}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public Mindmelter(final Mindmelter card) {
        super(card);
    }

    @Override
    public Mindmelter copy() {
        return new Mindmelter(this);
    }
}

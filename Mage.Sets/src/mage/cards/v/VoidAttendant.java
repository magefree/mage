
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileOpponentsCardFromExileToGraveyardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.EldraziScionToken;

/**
 *
 * @author LevelX2
 */
public final class VoidAttendant extends CardImpl {

    public VoidAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.PROCESSOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // {1}{G}, Put a card an opponent owns from exile into that player's graveyard: Create a 1/1 colorless Eldrazi Scion creature token. It has "Sacrifice this creature: Add {C}."
        Effect effect = new CreateTokenEffect(new EldraziScionToken());
        effect.setText("create a 1/1 colorless Eldrazi Scion creature token. It has \"Sacrifice this creature: Add {C}.\"");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new ExileOpponentsCardFromExileToGraveyardCost(true));
        this.addAbility(ability);

    }

    private VoidAttendant(final VoidAttendant card) {
        super(card);
    }

    @Override
    public VoidAttendant copy() {
        return new VoidAttendant(this);
    }
}

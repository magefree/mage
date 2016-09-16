package mage.sets.kaladesh;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

import java.util.UUID;

/**
 * Created by IGOUDT on 16-9-2016.
 */
public class GlimmerOfGenius extends CardImpl {

    public GlimmerOfGenius(final UUID ownerId){
        super(ownerId,49,"Glimmer of Genius", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT},"{3U}");
        this.expansionSetCode = "KLD";
        this.getSpellAbility().addEffect(new ScryEffect(2));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2));
    }

    public GlimmerOfGenius(final GlimmerOfGenius glimmerOfGenius){
        super(glimmerOfGenius);
    }

    public GlimmerOfGenius copy(){
        return new GlimmerOfGenius(this);
    }

}

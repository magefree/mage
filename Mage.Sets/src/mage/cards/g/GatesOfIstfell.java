package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GatesOfIstfell extends CardImpl {

    public GatesOfIstfell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Gates of Istfell enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {2}{W}{U}{U}, {T}, Sacrifice Gates of Istfell: You gain 2 life and draw two cards.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(2), new ManaCostsImpl<>("{2}{W}{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private GatesOfIstfell(final GatesOfIstfell card) {
        super(card);
    }

    @Override
    public GatesOfIstfell copy() {
        return new GatesOfIstfell(this);
    }
}



package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

/**
 * @author spjspj
 */
public final class NighteyesTheDesecratorToken extends TokenImpl {

    public NighteyesTheDesecratorToken() {
        super("Nighteyes the Desecrator Token", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        subtype.add(SubType.WIZARD);
        power = new MageInt(4);
        toughness = new MageInt(2);
        // {4}{B}: Put target creature card from a graveyard onto the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{4}{B}"));
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.addAbility(ability);
    }

    private NighteyesTheDesecratorToken(final NighteyesTheDesecratorToken token) {
        super(token);
    }

    public NighteyesTheDesecratorToken copy() {
        return new NighteyesTheDesecratorToken(this);
    }
}

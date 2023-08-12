package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

public final class FeatherToken extends TokenImpl {

    private static final FilterCard filter = new FilterCard("Phoenix card");

    static {
        filter.add(SubType.PHOENIX.getPredicate());
    }

    public FeatherToken() {
        super("Feather", "red artifact token named Feather with \"{1}, Sacrifice Feather: Return target Phoenix card from your graveyard to the battlefield tapped.\"");
        this.cardType.add(CardType.ARTIFACT);
        this.color.setRed(true);
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private FeatherToken(final FeatherToken token) {
        super(token);
    }

    public FeatherToken copy() {
        return new FeatherToken(this);
    }
}

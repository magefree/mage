package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvalancheCaller extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("snow land you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public AvalancheCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}: Target snow land you control becomes a 4/4 Elemental creature with haste and hexproof until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureTargetEffect(
                new AvalancheCallerToken(), false, true, Duration.EndOfTurn
        ), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AvalancheCaller(final AvalancheCaller card) {
        super(card);
    }

    @Override
    public AvalancheCaller copy() {
        return new AvalancheCaller(this);
    }
}

class AvalancheCallerToken extends TokenImpl {

    AvalancheCallerToken() {
        super("", "4/4 Elemental creature with hexproof and haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(HexproofAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private AvalancheCallerToken(final AvalancheCallerToken token) {
        super(token);
    }

    public AvalancheCallerToken copy() {
        return new AvalancheCallerToken(this);
    }
}

package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClanGuildmage extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent();

    public ClanGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}, {T}: Target creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}{G}, {T}: Target land you control becomes a 4/4 Elemental creature with haste until end of turn. It's still a land.
        ability = new SimpleActivatedAbility(new BecomesCreatureTargetEffect(
                new ClanGuildmageToken(), false, true, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ClanGuildmage(final ClanGuildmage card) {
        super(card);
    }

    @Override
    public ClanGuildmage copy() {
        return new ClanGuildmage(this);
    }
}

class ClanGuildmageToken extends TokenImpl {

    public ClanGuildmageToken() {
        super("", "4/4 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(HasteAbility.getInstance());
    }

    public ClanGuildmageToken(final ClanGuildmageToken token) {
        super(token);
    }

    public ClanGuildmageToken copy() {
        return new ClanGuildmageToken(this);
    }
}

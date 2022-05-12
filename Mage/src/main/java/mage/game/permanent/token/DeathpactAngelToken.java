

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author spjspj
 */
public final class DeathpactAngelToken extends TokenImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("card named Deathpact Angel from your graveyard");

    static {
        filter.add(new NamePredicate("Deathpact Angel"));
    }

    public DeathpactAngelToken() {
        super("Cleric Token", "1/1 white and black Cleric creature token. It has \"{3}{W}{B}{B}, {T}, Sacrifice this creature: Return a card named Deathpact Angel from your graveyard to the battlefield.\"");
        cardType.add(CardType.CREATURE);

        color.setWhite(true);
        color.setBlack(true);

        subtype.add(SubType.CLERIC);

        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl("{3}{W}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public DeathpactAngelToken(final DeathpactAngelToken token) {
        super(token);
    }

    public DeathpactAngelToken copy() {
        return new DeathpactAngelToken(this);
    }
}

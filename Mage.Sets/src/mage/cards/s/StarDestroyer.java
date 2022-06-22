
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TIEFighterToken;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class StarDestroyer extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("artifact creature");
    private static final FilterCreatureOrPlayer filter3 = new FilterCreatureOrPlayer("non-Starship creature or player");

    static {
        filter1.add(CardType.ARTIFACT.getPredicate());
        filter3.getCreatureFilter().add(Predicates.not(SubType.STARSHIP.getPredicate()));
    }

    public StarDestroyer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{B}{R}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // {2}{U}: Tap target artifact creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetCreaturePermanent(filter1));
        this.addAbility(ability);

        // {2}{B}: Create a 1/1 black Starship artifact creature token with spaceflight named TIE Fighter.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new TIEFighterToken()), new ManaCostsImpl<>("{2}{B}")));

        // {2}{R}: Star Destroyer deals 2 damge to target non-Starship creature or player.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetCreatureOrPlayer(filter3));
        this.addAbility(ability);
    }

    private StarDestroyer(final StarDestroyer card) {
        super(card);
    }

    @Override
    public StarDestroyer copy() {
        return new StarDestroyer(this);
    }
}


package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class ChampionOfStraySouls extends CardImpl {

    private final UUID originalId;
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creatures");

    static {
        filter.add(new AnotherPredicate());
    }

    public ChampionOfStraySouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        /**
         * You choose the targets of the first ability as you activate that
         * ability, before you pay any costs. You can't target any of the
         * creatures you sacrifice.
         */
        //TODO: Make ability properly copiable
        // {3}{B}{B}, {T}, Sacrifice X other creatures: Return X target creatures from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return X target creatures from your graveyard to the battlefield");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{3}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeXTargetCost(filter));
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, new FilterCreatureCard("creature cards from your graveyard")));
        originalId = ability.getOriginalId();
        this.addAbility(ability);

        // {5}{B}{B}: Put Champion of Stray Souls on top of your library from your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD,
                new PutOnLibrarySourceEffect(true, "Put {this} on top of your library from your graveyard"),
                new ManaCostsImpl("{5}{B}{B}")));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
                    int xValue = new GetXValue().calculate(game, ability, null);
                    ability.getTargets().clear();
                    ability.addTarget(new TargetCardInYourGraveyard(xValue, xValue, new FilterCreatureCard("creature cards from your graveyard")));
                }
            }
        }
    }

    public ChampionOfStraySouls(final ChampionOfStraySouls card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public ChampionOfStraySouls copy() {
        return new ChampionOfStraySouls(this);
    }
}

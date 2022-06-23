
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class WillowPriestess extends CardImpl {
    
    private static final FilterPermanentCard filter = new FilterPermanentCard("Faerie");
    private static final FilterCreaturePermanent greenCreature = new FilterCreaturePermanent("green creature");

    static {
        filter.add(SubType.FAERIE.getPredicate());
        greenCreature.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public WillowPriestess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: You may put a Faerie permanent card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(filter),
                new TapSourceCost()));
        
        // {2}{G}: Target green creature gains protection from black until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(ProtectionAbility.from(ObjectColor.BLACK), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}"));
        Target target = new TargetPermanent(greenCreature);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private WillowPriestess(final WillowPriestess card) {
        super(card);
    }

    @Override
    public WillowPriestess copy() {
        return new WillowPriestess(this);
    }
}


package mage.cards.g;

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
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class GoblinWizard extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Goblin");
    private static final FilterPermanent goblinPermanent = new FilterPermanent("Goblin");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
        goblinPermanent.add(SubType.GOBLIN.getPredicate());
    }

    public GoblinWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.rarity = Rarity.RARE;

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: You may put a Goblin permanent card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(filter),
                new TapSourceCost()));

        // {R}: Target Goblin gains protection from white until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(ProtectionAbility.from(ObjectColor.WHITE), Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        Target target = new TargetPermanent(goblinPermanent);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    private GoblinWizard(final GoblinWizard card) {
        super(card);
    }

    @Override
    public GoblinWizard copy() {
        return new GoblinWizard(this);
    }
}

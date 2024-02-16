package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBlockingCreature;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilentAssassin extends CardImpl {

    private static final FilterPermanent filter = new FilterBlockingCreature();

    public SilentAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {3}{B}: Destroy target blocking creature at end of combat.
        Ability ability = new SimpleActivatedAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(
                        new DestroyTargetEffect().setText("destroy target blocking creature")
                ), true
        ), new ManaCostsImpl<>("{3}{B}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SilentAssassin(final SilentAssassin card) {
        super(card);
    }

    @Override
    public SilentAssassin copy() {
        return new SilentAssassin(this);
    }
}

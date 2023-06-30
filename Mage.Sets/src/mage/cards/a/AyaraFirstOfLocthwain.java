package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyaraFirstOfLocthwain extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("black creature");
    private static final FilterControlledPermanent filter2
            = new FilterControlledCreaturePermanent("another black creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter2.add(new ColorPredicate(ObjectColor.BLACK));
        filter2.add(AnotherPredicate.instance);
    }

    public AyaraFirstOfLocthwain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Ayara, First of Locthwain or another black creature enters the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new LoseLifeOpponentsEffect(1), filter, false, true
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {T}, Sacrifice another black creature: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
    }

    private AyaraFirstOfLocthwain(final AyaraFirstOfLocthwain card) {
        super(card);
    }

    @Override
    public AyaraFirstOfLocthwain copy() {
        return new AyaraFirstOfLocthwain(this);
    }
}

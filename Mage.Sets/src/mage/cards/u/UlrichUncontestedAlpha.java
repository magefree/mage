package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UlrichUncontestedAlpha extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("non-Werewolf creature you don't control");

    static {
        filter.add(Predicates.not(SubType.WEREWOLF.getPredicate()));
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public UlrichUncontestedAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setRed(true);
        this.color.setGreen(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Whenever this creature transforms into Ulrich, Uncontested Alpha, you may have it fight target non-Werewolf creature you don't control.
        Ability ability = new TransformIntoSourceTriggeredAbility(
                new FightTargetSourceEffect()
                        .setText("you may have it fight target non-Werewolf creature you don't control"),
                true, true
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Ulrich, Uncontested Alpha.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private UlrichUncontestedAlpha(final UlrichUncontestedAlpha card) {
        super(card);
    }

    @Override
    public UlrichUncontestedAlpha copy() {
        return new UlrichUncontestedAlpha(this);
    }
}

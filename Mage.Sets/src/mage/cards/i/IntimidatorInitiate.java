package mage.cards.i;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class IntimidatorInitiate extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("red spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    private static final String rule = "Whenever a player casts a red spell, you may pay {1}. If you do, target creature can't block this turn.";

    public IntimidatorInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player casts a red spell, you may pay {1}. If you do, target creature can't block this turn.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new CantBlockTargetEffect(Duration.EndOfTurn), new GenericManaCost(1)), filter, false, rule);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private IntimidatorInitiate(final IntimidatorInitiate card) {
        super(card);
    }

    @Override
    public IntimidatorInitiate copy() {
        return new IntimidatorInitiate(this);
    }
}

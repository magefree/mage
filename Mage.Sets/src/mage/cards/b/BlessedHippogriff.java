package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlessedHippogriff extends AdventureCard {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public BlessedHippogriff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{W}", "Tyr's Blessing", "{W}");

        this.subtype.add(SubType.HIPPOGRIFF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Blessed Hippogriff attacks, target attacking creature without flying gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Tyr's Blessing
        // Target creature gains indestructible until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BlessedHippogriff(final BlessedHippogriff card) {
        super(card);
    }

    @Override
    public BlessedHippogriff copy() {
        return new BlessedHippogriff(this);
    }
}

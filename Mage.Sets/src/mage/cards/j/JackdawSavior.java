package mage.cards.j;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class JackdawSavior extends CardImpl {
    private static final FilterControlledCreaturePermanent flyingFilter = new FilterControlledCreaturePermanent("creature you control with flying");
    private static final FilterCard filterCard = new FilterCreatureCard("another target creature card with lesser mana value from your graveyard");

    static {
        flyingFilter.add(new AbilityPredicate(FlyingAbility.class));
        filterCard.add(JackdawSaviorPredicate.instance);
    }

    public JackdawSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Jackdaw Savior or another creature you control with flying dies, return another target creature card with lesser mana value from your graveyard to the battlefield.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false, flyingFilter);
        ability.addTarget(new TargetCardInYourGraveyard(filterCard));
        this.addAbility(ability);
    }

    private JackdawSavior(final JackdawSavior card) {
        super(card);
    }

    @Override
    public JackdawSavior copy() {
        return new JackdawSavior(this);
    }
}

enum JackdawSaviorPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        Permanent diedCreature = CardUtil.getEffectValueFromAbility(input.getSource(), "creatureDied", Permanent.class).orElse(null);
        return diedCreature != null
                && !input.getObject().getId().equals(diedCreature.getId())
                && input.getObject().getManaValue() < diedCreature.getManaValue();
    }
}

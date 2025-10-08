package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.ChooseCardTypeEffect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.keyword.WebSlingingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ChosenCardTypePredicate;
import mage.target.common.TargetOpponent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Jmlundeen
 */
public final class ArachnePsionicWeaver extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells of the chosen type");

    static {
        filter.add(ChosenCardTypePredicate.TRUE);
    }

    public ArachnePsionicWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Web-slinging {W}
        this.addAbility(new WebSlingingAbility(this, "{W}"));

        // As Arachne enters, look at an opponent’s hand, then choose a card type other than creature.
        List<CardType> types = Arrays.stream(CardType.values()).filter(cardType -> cardType != CardType.CREATURE)
                .collect(Collectors.toList());
        Ability ability = new AsEntersBattlefieldAbility(new OneShotNonTargetEffect(
                new LookAtTargetPlayerHandEffect().setText("look at an opponent's hand"), new TargetOpponent()));
        ability.addEffect(new ChooseCardTypeEffect(Outcome.Benefit, types)
                .setText("choose a card type other than creature")
                .concatBy(", then"));
        this.addAbility(ability);

        // Spells of the chosen type cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostIncreasingAllEffect(1, filter, TargetController.ANY)));
    }

    private ArachnePsionicWeaver(final ArachnePsionicWeaver card) {
        super(card);
    }

    @Override
    public ArachnePsionicWeaver copy() {
        return new ArachnePsionicWeaver(this);
    }
}

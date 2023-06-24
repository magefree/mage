package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallidusAssassin extends CardImpl {

    public CallidusAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Polymorphine -- You may have Callidus Assassin enter the battlefield tapped as a copy of any creature on the battlefield, except it has "When this creature enters the battlefield, destroy up to one other target creature with the same name as this creature."
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true).setText("tapped"), true
        );
        ability.addEffect(new CopyPermanentEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, new CallidusAssassinApplier()
        ));
        this.addAbility(ability.withFlavorWord("Polymorphine"));
    }

    private CallidusAssassin(final CallidusAssassin card) {
        super(card);
    }

    @Override
    public CallidusAssassin copy() {
        return new CallidusAssassin(this);
    }
}

class CallidusAssassinApplier extends CopyApplier {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("other target creature with the same name as this creature");

    static {
        filter.add(CallidusAssassinPredicate.instance);
        filter.add(AnotherPredicate.instance);
    }

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect())
                .setTriggerPhrase("When this creature enters the battlefield, ");
        ability.addTarget(new TargetPermanent(0, 1, filter));
        blueprint.getAbilities().add(ability);
        return true;
    }

    @Override
    public String getText() {
        return ", except it has \"When this creature enters the battlefield, " +
                "destroy up to one other target creature with the same name as this creature.\"";
    }
}

enum CallidusAssassinPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return CardUtil.haveSameNames(input.getObject(), input.getSource().getSourcePermanentOrLKI(game));
    }
}

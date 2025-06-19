package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrdruunMentor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that's attacking that player");

    static {
        filter.add(OrdruunMentorPredicate.instance);
    }

    public OrdruunMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Mentor
        this.addAbility(new MentorAbility());

        // Whenever you attack a player, target creature that's attacking that player gains first strike until end of turn.
        Ability ability = new AttacksPlayerWithCreaturesTriggeredAbility(
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()), SetTargetPointer.NONE);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OrdruunMentor(final OrdruunMentor card) {
        super(card);
    }

    @Override
    public OrdruunMentor copy() {
        return new OrdruunMentor(this);
    }
}
enum OrdruunMentorPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return CardUtil.getEffectValueFromAbility(input.getSource(), "playerAttacked", UUID.class)
                .filter(uuid -> uuid.equals(game.getCombat().getDefenderId(input.getObject().getId())))
                .isPresent();
    }
}
